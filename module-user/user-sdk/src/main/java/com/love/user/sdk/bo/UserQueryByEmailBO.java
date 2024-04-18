package com.love.user.sdk.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryByEmailBO implements Serializable {
    private String email;
    private boolean simple = true;
    private boolean silent;
}
