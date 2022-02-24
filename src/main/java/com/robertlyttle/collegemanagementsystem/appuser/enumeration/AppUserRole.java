package com.robertlyttle.collegemanagementsystem.appuser.enumeration;

import com.google.common.collect.Lists;
import lombok.Getter;
import java.util.List;

@Getter
public enum AppUserRole {

    ROLE_STUDENT(Lists.newArrayList(
            "student:read", "student:update")),
    ROLE_ADMIN(Lists.newArrayList(
                    "student:read", "student:update", "student:create",
                    "instructor:read", "instructor:update", "instructor:create",
                    "course:read", "course:update", "course:create",
                    "book:read", "book:update", "book:create")),
    ROLE_SUPER_ADMIN(Lists.newArrayList(
            "student:read", "student:update", "student:create", "student:delete",
            "instructor:read", "instructor:update", "instructor:create", "instructor:delete",
            "course:read", "course:update", "course:create", "course:delete",
            "book:read", "book:update", "book:create", "book:delete"));

    private final List<String> authorities;

    AppUserRole(List<String> authorities) {
        this.authorities = authorities;
    }
}
