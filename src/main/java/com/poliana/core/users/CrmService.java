package com.poliana.core.users;

import org.springframework.http.MediaType;

/**
 * @author David Gilmore
 * @date 2/24/14
 */
public interface CrmService {

    ProfilePhoto readUserProfilePhoto(long userId);

    void writeUserProfilePhoto(long userId, MediaType mediaType, byte[] bytesForProfilePhoto);

    User findById(long userId);

    User createUser(String username, String password, String firstName, String lastName);

    User removeUser(long userId);

    User updateUser(long userId, String username, String password, String firstName, String lastName);

    User findUserByUsername(String username);
}
