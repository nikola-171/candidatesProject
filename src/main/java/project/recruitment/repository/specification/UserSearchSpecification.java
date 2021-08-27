package project.recruitment.repository.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import project.recruitment.model.entity.CandidateEntity_;
import project.recruitment.model.entity.UserEntity;
import project.recruitment.model.entity.UserEntity_;
import project.recruitment.searchOptions.UserSearchOptions;

import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserSearchSpecification implements Specification<UserEntity>
{
    final UserSearchOptions _userSearchOptions;
    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
    {
        final Path<String> firstName = root.get(UserEntity_.firstName);
        final Path<String> lastName = root.get(UserEntity_.lastName);
        final Path<String> userName = root.get(UserEntity_.username);
        final Path<String> email = root.get(UserEntity_.email);
        final Path<ZonedDateTime> deleteDate = root.get(UserEntity_.deleteDate);

        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(_userSearchOptions.getFirstName()))
        {
            predicates.add(criteriaBuilder.like(firstName, "%"+_userSearchOptions.getFirstName()+"%"));
        }

        if(StringUtils.hasText(_userSearchOptions.getUsername()))
        {
            predicates.add(criteriaBuilder.like(userName, "%"+_userSearchOptions.getUsername()+"%"));
        }

        if(StringUtils.hasText(_userSearchOptions.getLastName()))
        {
            predicates.add(criteriaBuilder.like(lastName, "%"+_userSearchOptions.getLastName()+"%"));
        }

        if(StringUtils.hasText(_userSearchOptions.getEmail()))
        {
            predicates.add(criteriaBuilder.like(email, "%"+_userSearchOptions.getEmail()+"%"));
        }

        predicates.add(criteriaBuilder.isNull(deleteDate));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
