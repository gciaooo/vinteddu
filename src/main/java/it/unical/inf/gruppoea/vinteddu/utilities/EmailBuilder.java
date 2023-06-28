package it.unical.inf.gruppoea.vinteddu.utilities;

public class EmailBuilder {

    public String getTesto(int n, String parametro){

        switch (n){
            case 1:
                return EmailRepository.registrazione;
            case 2:
                var string= EmailRepository.recupera_password.replace("%s", parametro);
                return string;
        }

        return null;
    }




}
