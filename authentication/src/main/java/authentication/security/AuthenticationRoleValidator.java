package authentication.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class AuthenticationRoleValidator extends BaseValidator {


    /**
     * add roles to certain endpoints to restrict access.
     *
     * @param http configures security
     */
    @Override
    public void handle(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PUT, "/student/accept").denyAll();
        http.authorizeRequests()
                .antMatchers(GET,  "/student/getAll").denyAll();
        http.authorizeRequests()
                .antMatchers(GET, "/student/get")
                .hasAnyAuthority("ROLE_student", "ROLE_admin", "ROLE_lecturer");
        http.authorizeRequests()
                .antMatchers(PUT, "/student/apply").hasAnyAuthority("ROLE_student");
        http.authorizeRequests()
                .antMatchers(PUT, "/student/accept")
                .hasAnyAuthority("ROLE_admin");
        http.authorizeRequests()
                .antMatchers(GET, "/student/getTACourses",
                        "/student/getPassedCourses", "/student/getCandidateCourses")
                .hasAnyAuthority("ROLE_student", "ROLE_admin", "ROLE_lecturer");
        http.authorizeRequests()
                .antMatchers(GET, "/lecturer/getAll").hasAnyAuthority("ROLE_admin");
        http.authorizeRequests()
                .antMatchers(POST, "/courses/makeCourse",
                        "/student/add").hasAnyAuthority("ROLE_admin");
        http.authorizeRequests()
                .antMatchers(PUT, "/courses/updateSize", "lecturer/courses/addCourse")
                .hasAnyAuthority("ROLE_admin");
        http.authorizeRequests()
                .antMatchers(GET, "/courses/addLecturer")
                .hasAnyAuthority("ROLE_admin", "ROLE_lecturer");
        http.authorizeRequests()
                .antMatchers(GET, "/authentication/**").hasAnyAuthority("ROLE_admin");
        http.authorizeRequests()
                .antMatchers(GET, "/student/**").hasAnyAuthority("ROLE_student");
        http.authorizeRequests()
                .antMatchers(GET, "/lecturer/**").hasAnyAuthority("ROLE_lecturer");
        http.authorizeRequests()
                .antMatchers(GET, "/courses/**").hasAnyAuthority("ROLE_admin");
        http.authorizeRequests()
                .antMatchers(GET, "/management/**").hasAnyAuthority("ROLE_lecturer", "ROLE_admin");
        http.authorizeRequests().anyRequest().authenticated();

        super.checkNext(http);
    }
}
