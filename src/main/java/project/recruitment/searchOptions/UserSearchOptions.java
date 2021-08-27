package project.recruitment.searchOptions;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserSearchOptions
{
    String firstName;

    String lastName;

    String email;

    String username;
}
