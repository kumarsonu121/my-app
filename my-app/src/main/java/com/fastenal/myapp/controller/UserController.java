package com.fastenal.myapp.controller;

import com.fastenal.myapp.dto.Admin;
import com.fastenal.myapp.dto.Cart;
import com.fastenal.myapp.dto.Courses;
import com.fastenal.myapp.dto.Users;
import com.fastenal.myapp.password.Decryption;
import com.fastenal.myapp.password.Encryption;
import com.fastenal.myapp.password.HashingAlgorithm;
import com.fastenal.myapp.repository.AdminRepository;
import com.fastenal.myapp.repository.CartRepository;
import com.fastenal.myapp.repository.CourseRepository;
import com.fastenal.myapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository repo;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    CourseRepository courseRepository;

    @RequestMapping("/users")
    public List<Users> getUsers() {
        LOGGER.info("UserController:getUsers entered the method");
        try {
            List<Users> users = (List<Users>) repo.findAll();
            LOGGER.info("UserController:getUsers exiting the method");
            return users;
        } catch(Exception e) {
            LOGGER.error("can not retrieve data from customers table" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(
            value ="/save",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
              )
        public String saveUsers(@RequestBody Users user) {
        LOGGER.info("UserController:saveUsers entered the method");
        try {
            String text = user.getPassword();
            if (text == null) {
                LOGGER.error("password can not be null");
            }
            String email = user.getEmail();
            String newText = Encryption.passwordEncyption(text, email);
            if(newText == null) {
                return "not hashed";
            }
            user.setPassword(newText);
            repo.save(user);
            LOGGER.info("UserController:saveUsers exiting the method");
            return "saved";
        } catch(Exception e) {
            LOGGER.error("can not save data to the database" + e.getMessage());
            return "not saved";
        }
    }

    @RequestMapping(
            value = "/login",
            produces = {MediaType.APPLICATION_JSON_VALUE}
              )
        public Users loginUsers(@RequestBody Map<String, String> data) {
        LOGGER.info("UserController:loginUsers entered the method");
        try {
            String username = data.get("username");
            if (username == null) {
                LOGGER.error("username can not be null");
            }
            String passcode = data.get("password");
            if (passcode == null) {
                LOGGER.error("passcode can not be null");
            }
            Users user = repo.findByName(username);
            if (user == null) {
                LOGGER.error("username not found/Incorrect user login data");
                return null;
            }
            String email = user.getEmail();
            String hashedPassword = user.getPassword();
            if (Decryption.checkPassword(passcode, hashedPassword, email)) {
                LOGGER.info("UserController:loginUsers exiting the method");
                return user;
            }
            LOGGER.warn("password did not match");
            return null;
        } catch (Exception e) {
            LOGGER.error("can not login user" + e.getMessage());
            return null;
        }

    }

    @RequestMapping(
            value = "/addCourse",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Users addCourse(@RequestBody Map<String, String> data) {
        LOGGER.info("UserController:addCourse entered the method");
        String username = data.get("username");
        if(username == null) {
            LOGGER.error("username can not be null");
        }
        String name = data.get("Category");
        if(name == null) {
            LOGGER.error("Category can not be null");
        }
          try {
              Users user = repo.findByName(username);
              Courses courseValue = courseRepository.findByNames(name);
              user.setCourse(courseValue);
              repo.save(user);
              LOGGER.info("course added to the user account");
              LOGGER.info("UserController:addCourse exiting the method");
              return user;
          }
          catch(Exception e) {
             LOGGER.error("exception occurred in UserController:addCourse!"+ e.getMessage());
              return null;
          }
    }

    @RequestMapping(
            value = "/returnCourse",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Set<Courses> returnCourse(@RequestBody Map<String, String> data) {
        LOGGER.info("UserController:returnCourse entered the method");
        try {
            String username = data.get("username");
            if (username == null) {
                LOGGER.error("username can not be null");
            }
            Users user = repo.findByName(username);
            Set<Courses> course = user.getCourse();
            LOGGER.info("UserController:returnCourse exiting the method");
            return course;
        } catch(Exception e) {
            LOGGER.error("can not find the course" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(
            value = "/reRender",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Users reRender(@RequestBody Map<String, String> data) {
        LOGGER.info("UserController:reRender entered the method");
        try {
            String username = data.get("username");
            if (username == null) {
                LOGGER.error("username can not be null");
            }
            Users user = repo.findByName(username);
            LOGGER.info("UserController:reRender exiting the method");
            return user;
        } catch(Exception e) {
            LOGGER.error("can not find the user" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/getCart",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Cart getCart(@RequestBody Map<String, String> data) {
        LOGGER.info("UserController:getCart entered the method");
        try {
            String c_name = data.get("name");
            if (c_name == null) {
                LOGGER.error("course name can not be null");
            }
            Cart cart = cartRepository.findByName(c_name);
            LOGGER.info("UserController:getCart exiting the method");
            return cart;
        } catch (Exception e) {
            LOGGER.error("can not retrieve cart item" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(
            value ="/saveAdmin",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public String saveAdmin(@RequestBody Admin admin) {
        try {
            LOGGER.info("UserController:saveAdmin entered the method");
            String text = admin.getPassword();
            if (text == null) {
                LOGGER.error("password can not be null");
            }
            String newText = HashingAlgorithm.hashText(text);
            admin.setPassword(newText);
            adminRepository.save(admin);
            LOGGER.info("UserController:saveAdmin exiting the method");
            return "saved";
        } catch (Exception e) {
            LOGGER.error("can not create admin account" + e.getMessage());
            return "not saved";
        }
    }

    @RequestMapping(
            value = "/loginAdmin",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Admin loginAdmin(@RequestBody Map<String, String> data) {
        LOGGER.info("UserController:loginAdmin entered the method");
        try {
            String username = data.get("username");
            if (username == null) {
                LOGGER.error("username can not be null");
            }
            String passcode = data.get("password");
            if (passcode == null) {
                LOGGER.error("passcode can not be null");
            }
            Admin admin = adminRepository.findByName(username);
            if (admin == null) {
                LOGGER.error("Incorrect username entered");
                return null;
            }
            String hashedPassword = admin.getPassword();
            if (hashedPassword.equals(HashingAlgorithm.hashText(passcode))) {
                LOGGER.info("UserController:loginAdmin exiting the method");
                return admin;
            }
            LOGGER.warn("password did not match");
            return null;
        } catch (Exception e) {
            LOGGER.error("can not login admin" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(
            value = "/deleteUser",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public String deleteUser(@RequestBody Map<String, String> data) {
        LOGGER.info("UserController: deleteUser entered the method");
        try {
            String id = data.get("id");
            repo.deleteById(Integer.valueOf(id));
            LOGGER.info("UserController: deleteUser exiting the method");
            return "deleted";
        } catch(Exception e) {
            LOGGER.error("can not delete user" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(
            value = "/countUser"
    )
    public long countUser() {
        LOGGER.info("UserController: countUser entered the method");
        try {
            LOGGER.info("UserController: countUser exiting the method");
            return repo.count();
        } catch(Exception e) {
            LOGGER.error("can not count database records" + e.getMessage());
            return -1;
        }
    }

}
