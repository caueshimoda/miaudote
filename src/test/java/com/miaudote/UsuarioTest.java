package com.miaudote;


import com.miaudote.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    void isValidNome_withValidNome_ShouldReturnTrue(){
        //uso de classe anônima
        Usuario usuario = new Usuario(){
            @Override
            public String getNome(){
                return "Bruna Ogura";
            }
        };

        assertTrue(usuario.isValidNome());
    }

    @Test
    void isValidNome_withEmptyNome_ShouldReturnFalse(){
        Usuario usuario = new Usuario(){
            @Override
            public String getNome(){
                return "";
            }
        };

        assertFalse(usuario.isValidNome());
    }

    @Test
    void isValidNome_withNullNome_ShouldReturnFalse(){
        Usuario usuario = new Usuario(){
            @Override
            public String getNome(){
                return null;
            }
        };

        assertFalse(usuario.isValidNome());
    }

    @Test
    void isValidNome_withNumbers_ShouldReturnFalse(){
        Usuario usuario = new Usuario(){
            @Override
            public String getNome(){
                return "samuel2";
            }
        };

        assertFalse(usuario.isValidNome());
    }

    @Test
    void isValidNome_withJustSpaces_ShouldReturnFalse(){
        Usuario usuario = new Usuario(){
            @Override
            public String getNome(){
                return "          ";
            }
        };

        assertFalse(usuario.isValidNome());
    }

    @Test
    void isValidNome_withLongNome_ShouldReturnFalse(){
        Usuario usuario = new Usuario(){
            @Override
            public String getNome(){
                return "a".repeat(61);
            }
        };

        assertFalse(usuario.isValidNome());
    }

    @Test
    void isValidEmail_withValidEmail_ShouldReturnTrue(){
        Usuario usuario = new Usuario(){
            @Override
            public String getEmail(){
                return "cauefshimoda@gmail.com";
            }
        };

        assertTrue(usuario.isValidEmail());
    }

    @Test
    void isValidEmail_withInvalidEmail_ShouldReturnFalse(){
        Usuario usuario = new Usuario(){
            @Override
            public String getEmail(){
                return "cauefshimodagmail.com";
            }
        };

        assertFalse(usuario.isValidEmail());
    }

    @Test
    void isValidEmail_withEmptyEmail_ShouldReturnFalse(){
        Usuario usuario = new Usuario(){
            @Override
            public String getEmail(){
                return "";
            }
        };

        assertFalse(usuario.isValidEmail());
    }

    @Test
    void isValidEmail_withNullEmail_ShouldReturnFalse(){
        Usuario usuario = new Usuario(){
            @Override
            public String getEmail(){
                return null;
            }
        };

        assertFalse(usuario.isValidEmail());
    }

    @Test
    void isValidEmail_withoutDomain_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getEmail() {
                return "cauefshimoda@";
            }
        };
        assertFalse(usuario.isValidEmail());
    }

    @Test
    void isValidEmail_withInvalidCharacter_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getEmail() {
                return "cauefshimoda@exemplo!.com";
            }
        };
        assertFalse(usuario.isValidEmail());
    }

    @Test
    void isValidSenha_withValidPassword_ShouldReturnTrue() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return "Abcdef1@";
            }
        };
        assertTrue(usuario.isValidSenha());
    }

    @Test
    void isValidSenha_withNullPassword_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return null;
            }
        };
        assertFalse(usuario.isValidSenha());
    }

    @Test
    void isValidSenha_withEmptyPassword_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return "";
            }
        };
        assertFalse(usuario.isValidSenha());
    }

    @Test
    void isValidSenha_withoutUppercase_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return "abcdef1@";
            }
        };
        assertFalse(usuario.isValidSenha());
    }

    // sem minúscula
    @Test
    void isValidSenha_withoutLowercase_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return "ABCDEF1@";
            }
        };
        assertFalse(usuario.isValidSenha());
    }

    @Test
    void isValidSenha_withoutDigit_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return "Abcdefg@";
            }
        };
        assertFalse(usuario.isValidSenha());
    }

    // sem caractere especial
    @Test
    void isValidSenha_withoutSpecialChar_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return "Abcdefg1";
            }
        };
        assertFalse(usuario.isValidSenha());
    }

    @Test
    void isValidSenha_withSpace_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return "Abc def1@";
            }
        };
        assertFalse(usuario.isValidSenha());
    }

    // menos de 8 caracteres
    @Test
    void isValidSenha_withShortPassword_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getSenha() {
                return "Ab1@cd";
            }
        };
        assertFalse(usuario.isValidSenha());
    }

    @Test
    void isValidTelefone_withValidNumber_ShouldReturnTrue() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return "11987654321";
            }
        };
        assertTrue(usuario.isValidTelefone());
    }

    // Telefone nulo
    @Test
    void isValidTelefone_withNull_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return null;
            }
        };
        assertFalse(usuario.isValidTelefone());
    }

    @Test
    void isValidTelefone_withEmpty_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return "";
            }
        };
        assertFalse(usuario.isValidTelefone());
    }

    // sem DDD
    @Test
    void isValidTelefone_withoutDDD_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return "987654321";
            }
        };
        assertFalse(usuario.isValidTelefone());
    }

    @Test
    void isValidTelefone_withoutNine_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return "1187654321";
            }
        };
        assertFalse(usuario.isValidTelefone());
    }

    // com caracteres especiais
    @Test
    void isValidTelefone_withSpecialCharacters_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return "(11)98765-4321";
            }
        };
        assertFalse(usuario.isValidTelefone());
    }

    // com espaços
    @Test
    void isValidTelefone_withSpaces_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return "11 987654321";
            }
        };
        assertFalse(usuario.isValidTelefone());
    }

    // número muito curto
    @Test
    void isValidTelefone_withShortNumber_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return "1198765432";
            }
        };
        assertFalse(usuario.isValidTelefone());
    }

    // mto longo
    @Test
    void isValidTelefone_withLongNumber_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getTelefone() {
                return "119876543210";
            }
        };
        assertFalse(usuario.isValidTelefone());
    }

    @Test
    void isValidNumEndereco_withValidNumber_ShouldReturnTrue() {
        Usuario usuario = new Usuario() {
            @Override
            public int getNumEndereco() {
                return 123;
            }
        };
        assertTrue(usuario.isValidNumEndereco());
    }

    @Test
    void isValidNumEndereco_withZero_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public int getNumEndereco() {
                return 0;
            }
        };
        assertFalse(usuario.isValidNumEndereco());
    }

    @Test
    void isValidNumEndereco_withNegativeNumber_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public int getNumEndereco() {
                return -10;
            }
        };
        assertFalse(usuario.isValidNumEndereco());
    }

    @Test
    void isValidComplEndereco_withValidComplement_ShouldReturnTrue() {
        Usuario usuario = new Usuario() {
            @Override
            public String getComplEndereco() {
                return "Apto 101";
            }
        };
        assertTrue(usuario.isValidComplEndereco());
    }

    @Test
    void isValidComplEndereco_withNullComplement_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getComplEndereco() {
                return null;
            }
        };
        assertFalse(usuario.isValidComplEndereco());
    }

    @Test
    void isValidComplEndereco_withEmptyComplement_ShouldReturnFalse() {
        Usuario usuario = new Usuario() {
            @Override
            public String getComplEndereco() {
                return "";
            }
        };
        assertFalse(usuario.isValidComplEndereco());
    }


    /*
    todo: eu poderia testar o método isValidUsuario tbm? Poderia, mas aí eu teria que testar absolutamente todos os casos possiveis dele, ou seja, testar todas as combinações possíveis dos testes acima
    enfim, seriam muitos testes que iam validar o que já validamos antes. então vou considerar testar cada isValid isolado como suficiente
     */

}







