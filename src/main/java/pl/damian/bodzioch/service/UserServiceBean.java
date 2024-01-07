package pl.damian.bodzioch.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.damian.bodzioch.exception.AppException;
import pl.damian.bodzioch.mapper.RepositoryMapper;
import pl.damian.bodzioch.model.UserModel;
import pl.damian.bodzioch.repository.UserRepository;
import pl.damian.bodzioch.repository.entity.UserEntity;
import pl.damian.bodzioch.service.intefraces.UserService;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceBean implements UserService {

    private final UserRepository userRepository;

    @Override
    public void saveUser(UserModel userModel) {
        if (userRepository.existsByUsername(userModel.getUsername())) {
            throw new AppException("service.userService.userAlreadyExists", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = RepositoryMapper.map(userModel);
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            log.error("An error occurred while saving user.", e);
            throw new AppException("service.userService.saveNewUserError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
