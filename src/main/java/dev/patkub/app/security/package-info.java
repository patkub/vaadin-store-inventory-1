/**
 * This package contains an initial security configuration for your application.
 * <p>
 * It provides the following features:
 * <ul>
 * <li>An application-specific user model for accessing user information regardless of the underlying identity
 * management implementation ({@link dev.patkub.app.security.AppUserInfo},
 * {@link dev.patkub.app.security.CurrentUser})</li>
 * <li>A value object for identifying users ({@link dev.patkub.app.security.domain.UserId})</li>
 * <li>Method-level security ({@link dev.patkub.app.security.CommonSecurityConfig})</li>
 * <li>A development mode security configuration with simple login and in-memory users ({@code dev} package)</li>
 * <li>A production mode security configuration for use with Vaadin Control Center ({@code controlcenter} package)</li>
 * </ul>
 * </p>
 * <p>
 * You can use the package as-is in your application or extend and modify it to your needs. If you want to build your
 * own security model from scratch, delete the package.
 * </p>
 */
@NullMarked
package dev.patkub.app.security;

import org.jspecify.annotations.NullMarked;
