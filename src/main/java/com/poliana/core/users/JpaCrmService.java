package com.poliana.core.users;

import com.poliana.config.ApplicationConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.*;

/**
 * Simple implementation of the {@link CrmService crmService} interface.
 *
 * @author Josh Long
 */
@Service
@Transactional
public class JpaCrmService implements CrmService {

    private UserRepository userRepository;

    @Inject
    public JpaCrmService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ProfilePhoto readUserProfilePhoto(long userId) {
        InputStream fileInputSteam = null;
        try {
            User user = findById(userId);
            fileInputSteam = new FileInputStream(fileForPhoto(userId));
            byte[] data = IOUtils.toByteArray(fileInputSteam);
            return new ProfilePhoto(userId, data, MediaType.parseMediaType(user.getProfilePhotoMediaType()));
        } catch (Exception e) {
            throw new UserProfilePhotoReadException(userId, e);
        } finally {
            IOUtils.closeQuietly(fileInputSteam);
        }
    }

    @Override
    public void writeUserProfilePhoto(long userId, MediaType ext, byte[] bytesForProfilePhoto) {

        User user = findById(userId);
        user.setProfilePhotoMediaType(ext.toString());
        user.setProfilePhotoImported(true);
        userRepository.save(user);

        ByteArrayInputStream byteArrayInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileForPhoto(userId));
            byteArrayInputStream = new ByteArrayInputStream(bytesForProfilePhoto);
            IOUtils.copy(byteArrayInputStream, fileOutputStream);
        } catch (IOException e) {
            throw new UserProfilePhotoWriteException(userId, e);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(byteArrayInputStream);
        }
    }

    @Override
    public User findById(long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User createUser(String username, String password, String firstName, String lastName) {
        User user = new User(username, password, firstName, lastName);
        this.userRepository.save(user);
        return user;
    }

    @Override
    public User removeUser(long userId) {
        User u = userRepository.findOne(userId);
        this.userRepository.delete(userId);
        return u;
    }

    @Override
    public User updateUser(long userId, String username, String password, String firstName, String lastName) {
        User user = userRepository.findOne(userId);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        return this.userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private File fileForPhoto(Long userId) {
        return new File(ApplicationConfig.CRM_STORAGE_PROFILES_DIRECTORY, Long.toString(userId));
    }

}

