package project.recruitment.repository.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import project.recruitment.model.entity.CandidateEntity;
import project.recruitment.model.entity.CandidateEntity_;
import project.recruitment.searchOptions.CandidateSearchOptions;

import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CandidateSearchSpecification implements Specification<CandidateEntity> {

    private final CandidateSearchOptions _candidateSearchOptions;

    @Override
    public Predicate toPredicate(Root<CandidateEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        final Path<String> firstName = root.get(CandidateEntity_.firstName);
        final Path<String> lastName = root.get(CandidateEntity_.lastName);
        final Path<String> userName = root.get(CandidateEntity_.username);
        final Path<String> email = root.get(CandidateEntity_.email);
        final Path<String> contactNumber = root.get(CandidateEntity_.contactNumber);
        final Path<String> cityOfLiving = root.get(CandidateEntity_.cityOfLiving);
        final Path<ZonedDateTime> deleteDate = root.get(CandidateEntity_.deleteDate);

        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(_candidateSearchOptions.getFirstName()))
        {
            predicates.add(criteriaBuilder.like(firstName, "%"+_candidateSearchOptions.getFirstName()+"%"));
        }

        if(StringUtils.hasText(_candidateSearchOptions.getUsername()))
        {
            predicates.add(criteriaBuilder.like(userName, "%"+_candidateSearchOptions.getUsername()+"%"));
        }

        if(StringUtils.hasText(_candidateSearchOptions.getLastName()))
        {
            predicates.add(criteriaBuilder.like(lastName, "%"+_candidateSearchOptions.getLastName()+"%"));
        }

        if(StringUtils.hasText(_candidateSearchOptions.getEmail()))
        {
            predicates.add(criteriaBuilder.like(email, "%"+_candidateSearchOptions.getEmail()+"%"));
        }

        if(StringUtils.hasText(_candidateSearchOptions.getContactNumber()))
        {
            predicates.add(criteriaBuilder.like(contactNumber, "%"+_candidateSearchOptions.getContactNumber()+"%"));
        }

        if(StringUtils.hasText(_candidateSearchOptions.getCityOfLiving()))
        {
            predicates.add(criteriaBuilder.like(cityOfLiving, "%"+_candidateSearchOptions.getCityOfLiving()+"%"));
        }

        predicates.add(criteriaBuilder.isNull(deleteDate));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
