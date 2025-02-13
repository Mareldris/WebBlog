package Web.WebBlog.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

    @Entity
    public class User implements UserDetails {

        @Id
        @GeneratedValue(generator = "SEQUENCE_USER", strategy = GenerationType.SEQUENCE)
        @SequenceGenerator(name = "SEQUENCE_USER", sequenceName = "SO.SEQUENCE_USER",allocationSize=1)
        private long id;

        public User() {
        }

        private String username,password,email,activationCode;

        private boolean active;

        @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
        @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
        @Enumerated(EnumType.STRING)
        private Set<Role> roles;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public User(String email,String username, String password) {
            this.email = email;
            this.username = username;
            this.password = password;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return isActive();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getRoles();
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }


        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
        public Set<Role> getRoles() {
            return roles;
        }

        public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getActivationCode() {
            return activationCode;
        }

        public void setActivationCode(String activationCode) {
            this.activationCode = activationCode;
        }
    }
