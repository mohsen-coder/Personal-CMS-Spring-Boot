package mohsen.coder.personalcmsblogspring.adapter.persistence;

import mohsen.coder.personalcmsblogspring.adapter.persistence.model.AccountEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends PagingAndSortingRepository<AccountEntity, Long> {
    Optional<AccountEntity> findAccountEntityByPublicId(String publicId);

    Optional<AccountEntity> findAccountEntityByEmail(String email);

    Optional<AccountEntity> findAccountEntityByUsername(String username);

    List<AccountEntity> findAllByRole(String role, Pageable pageable);

}
