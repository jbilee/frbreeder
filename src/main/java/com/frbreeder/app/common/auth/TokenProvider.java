package com.frbreeder.app.common.auth;

import com.frbreeder.app.domain.entity.Workspace;

public interface TokenProvider {

    String createToken(Workspace workspace);

    Long getWorkspaceIdFromToken(String token);

}
