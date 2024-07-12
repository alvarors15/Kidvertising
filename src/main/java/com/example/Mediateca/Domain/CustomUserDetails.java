/*
 * package com.example.Mediateca.Domain;
 * 
 * import org.springframework.security.core.userdetails.UserDetails;
 * import org.springframework.security.core.GrantedAuthority;
 * 
 * import java.util.Collection;
 * 
 * public class CustomUserDetails implements UserDetails {
 * 
 * private UserDO user;
 * 
 * public CustomUserDetails(UserDO user) {
 * this.user = user;
 * }
 * 
 * @Override
 * public Collection<? extends GrantedAuthority> getAuthorities() {
 * // return user.getRoles().stream()
 * // .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
 * // .collect(Collectors.toList());
 * return null;
 * }
 * 
 * @Override
 * public String getPassword() {
 * return user.getPassword();
 * }
 * 
 * @Override
 * public String getUsername() {
 * return user.getUsername();
 * }
 * 
 * // Implementar los otros métodos de UserDetails
 * // ...
 * 
 * public UserDO getUser() {
 * return user;
 * }
 * 
 * @Override
 * public boolean isAccountNonExpired() {
 * return true;
 * }
 * 
 * @Override
 * public boolean isAccountNonLocked() {
 * return true;
 * }
 * 
 * @Override
 * public boolean isCredentialsNonExpired() {
 * return true;
 * }
 * 
 * @Override
 * public boolean isEnabled() {
 * return true;
 * }
 * }
 */