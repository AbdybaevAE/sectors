package com.cifer.categories.services.users;

import com.cifer.categories.entities.User;
import com.cifer.categories.exceptions.BadRequestException;
import com.cifer.categories.exceptions.ServiceInternalException;
import com.cifer.categories.repos.SectorRepository;
import com.cifer.categories.repos.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;

    public UsersServiceImpl(UserRepository userRepository, SectorRepository sectorRepository) {
        this.userRepository = userRepository;
        this.sectorRepository = sectorRepository;
    }

    /**
     * Method that create or update user in database. According sessionId
     * @param userData
     */
    @Override
    public void createOrUpdate(UserData userData) {
//      Validate input data as data can be provided from untrusted sources. It's always better to have data validation in service layer.
//      In order to validate data firstName and sessionId must be not null and not empty strings and sectors must by not empty(or exist in database)
        if (CollectionUtils.isEmpty(userData.getSectors()) || Strings.isBlank(userData.getFirstName()) || Strings.isBlank(userData.getSessionId())) {
            throw new BadRequestException("wrong input data " + userData);
        }
        var user = new User();
        user.setFirstName(userData.getFirstName());
        user.setSessionId(userData.getSessionId());
//        Search sectors in db.
        var userSectors = sectorRepository.findByValueIn(userData.getSectors());
        if (userSectors.isEmpty()) throw new BadRequestException("provide at least one valid sector");
        user.setSectors(userSectors);
//        Try create new user. If we catch unique constraint violation error, then it means user with provided sessionId already exists in db. In that case just update(patch) user data.
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            updateUser(userData);
        } catch (Exception exception) {
            throw new ServiceInternalException(exception);
        }
    }

    /**
     * Update user data by sessionId. We know that user exist.
     * @param userData
     */
    private void updateUser(UserData userData) {
        var user = userRepository.findUserBySessionId(userData.getSessionId()).get();
        user.setFirstName(userData.getFirstName());
        var sectors = sectorRepository.findByValueIn(userData.getSectors());
        if (sectors.isEmpty()) throw new BadRequestException("provide at least one valid sector");
        user.setSectors(sectors);
        userRepository.save(user);
    }

    /**
     * Get user by session Id
     * @param sessionId
     * @return Optional<User>
     */
    @Override
    public Optional<User> getBySessionId(String sessionId) {
        return userRepository.findUserBySessionId(sessionId);
    }
}
