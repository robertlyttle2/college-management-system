package com.robertlyttle.collegemanagementsystem.appuser.enumeration;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

@Getter
public enum AppUserRole {

    ROLE_STUDENT(Lists.newArrayList(
            "student:read", "student:update")),
    ROLE_ENROLMENT_ADMIN(Lists.newArrayList(
            "enrolment:read")),
    ROLE_COURSE_ADMIN(Lists.newArrayList(
            "course:read", "course:update", "course:create", "course:delete")),
    ROLE_SUPER_ADMIN(Lists.newArrayList(
            "user:read", "user:update", "user:create", "user:delete",
            "student:read", "student:update", "student:create", "student:delete",
            "enrolment:read",
            "course:read", "course:update", "course:create", "course:delete"));

    private final List<String> authorities;

    AppUserRole(List<String> authorities) {
        this.authorities = authorities;
    }
}
