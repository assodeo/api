package org.assodeo.api.controller;

import org.assodeo.api.entity.Association;
import org.assodeo.api.service.MembershipService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/me")
public class MeController {

    private final MembershipService membershipService;

    public MeController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping("/associations")
    @PreAuthorize("isAuthenticated()")
    public Collection<Long> getAssociations(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());

        return membershipService.getAssociations(userId)
                .stream()
                .map(Association::getId)
                .toList();
    }
}
