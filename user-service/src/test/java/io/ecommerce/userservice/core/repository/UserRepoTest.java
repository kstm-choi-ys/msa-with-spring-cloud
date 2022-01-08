package io.ecommerce.userservice.core.repository;

import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.generator.UserGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author : choi-ys
 * @date : 2022/01/07 3:45 오후
 */
@DataJpaTest
@DisplayName("Repo:User")
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(UserGenerator.class)
class UserRepoTest {

    private final UserRepo userRepo;
    private final UserGenerator userGenerator;

    UserRepoTest(
            UserRepo userRepo,
            UserGenerator userGenerator
    ) {
        this.userRepo = userRepo;
        this.userGenerator = userGenerator;
    }

    @Test
    @DisplayName("회원 엔티티 저장")
    void save() {
        // Given
        String email = "test@gmail.com";
        String password = "password";
        String name = "choi-ys";

        User user = User.of(email, password, name);

        // When
        User expected = userRepo.save(user);

        // Then
        assertAll(
                () -> then(expected.getId()).isNotNull(),
                () -> then(expected.getEmail()).isEqualTo(email),
                () -> then(expected.getPassword()).isEqualTo(password),
                () -> then(expected.getName()).isEqualTo(name),
                () -> then(expected.getCreatedAt()).isNotNull(),
                () -> then(expected.getUpdatedAt()).isNotNull()
        );
    }

    @Test
    @DisplayName("회원 엔티티 조회")
    void findById() {
        // Given
        User savedUser = userGenerator.savedUser();

        // When
        User expected = userRepo.findById(savedUser.getId()).orElseThrow();

        // Then
        assertAll(
                () -> then(expected.getId()).isNotNull(),
                () -> then(expected.getEmail()).isEqualTo(savedUser.getEmail()),
                () -> then(expected.getPassword()).isEqualTo(savedUser.getPassword()),
                () -> then(expected.getName()).isEqualTo(savedUser.getName()),
                () -> then(expected.getCreatedAt()).isNotNull(),
                () -> then(expected.getUpdatedAt()).isNotNull()
        );
    }
}