package com.oneultanda.userservice.presentation.dto.request;

import com.oneultanda.userservice.application.dto.comand.UpdateUserRoleCommand;
import com.oneultanda.userservice.common.exception.ValidRole;
import com.oneultanda.userservice.domain.model.Role;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRoleRequest(

        @ValidRole(enumClass = Role.class)
        Role role
) {
    public UpdateUserRoleCommand toCommand() {
        return new UpdateUserRoleCommand(role);
    }
}
