/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.golang.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.repository.golang.rest.model.GolangHostedRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.AbstractRepositoriesApiResource;
import org.sonatype.nexus.repository.rest.api.AbstractRepositoryApiRequestToConfigurationConverter;
import org.sonatype.nexus.repository.rest.api.AuthorizingRepositoryManager;
import org.sonatype.nexus.repository.rest.api.RepositoriesApiResource;
import org.sonatype.nexus.validation.Validate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import static org.sonatype.nexus.common.app.FeatureFlags.FEATURE_GOLANG_HOSTED;
import static org.sonatype.nexus.repository.golang.rest.GolangHostedRepositoriesApiResource.RESOURCE_URI;
import static org.sonatype.nexus.rest.ApiDocConstants.API_REPOSITORY_MANAGEMENT;
import static org.sonatype.nexus.rest.ApiDocConstants.AUTHENTICATION_REQUIRED;
import static org.sonatype.nexus.rest.ApiDocConstants.INSUFFICIENT_PERMISSIONS;
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_CREATED;
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_NOT_FOUND;
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_UPDATED;

/**
 * REST resource for handling requests regarding hosted Golang repositories.
 *
 * @since 3.20
 */
@FeatureFlag(name = FEATURE_GOLANG_HOSTED)
@Api(value = API_REPOSITORY_MANAGEMENT)
@Named
@Singleton
@Path(RESOURCE_URI)
public class GolangHostedRepositoriesApiResource
    extends AbstractRepositoriesApiResource<GolangHostedRepositoryApiRequest>
{
  public static final String RESOURCE_URI = RepositoriesApiResource.RESOURCE_URI + "/go/hosted";

  @Inject
  public GolangHostedRepositoriesApiResource(
      final AuthorizingRepositoryManager authorizingRepositoryManager,
      final AbstractRepositoryApiRequestToConfigurationConverter<GolangHostedRepositoryApiRequest> configurationAdapter)
  {
    super(authorizingRepositoryManager, configurationAdapter);
  }

  @ApiOperation("Create a Go hosted repository")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = REPOSITORY_CREATED),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS)
  })
  @POST
  @RequiresAuthentication
  @Validate
  @Override
  public Response createRepository(final GolangHostedRepositoryApiRequest request) {
    return super.createRepository(request);
  }

  @ApiOperation("Update a Go hosted repository")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = REPOSITORY_UPDATED),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS),
      @ApiResponse(code = 404, message = REPOSITORY_NOT_FOUND)
  })
  @PUT
  @Path("/{repositoryName}")
  @RequiresAuthentication
  @Validate
  @Override
  public Response updateRepository(
      final GolangHostedRepositoryApiRequest request,
      @ApiParam(value = "Name of the repository to update") @PathParam("repositoryName") final String repositoryName)
  {
    return super.updateRepository(request, repositoryName);
  }
}