package com.szavazas.szavazas.validator;

import com.szavazas.szavazas.exception.NotFoundDomainBaseException;
import com.szavazas.szavazas.exception.ValidationDomainBaseException;
import com.szavazas.szavazas.repository.SzavazasRepository;
import com.szavazas.szavazas.request.SzavazasRequest;
import com.szavazas.szavazas.request.SzavazatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SzavazasValidator {

    public static final String A_MEGADOTT_AZONOSITOJU_SZAVAZAS_NEM_TALALHATO = "A megadott azonosítójú szavazás nem található!";
    public static final String EZZEL_AZ_IDOPONTTAL_MAR_LETEZIK_SZAVAZAS_AZ_ADATBAZISBAN = "Ezzel az időponttal már létezik szavazás az adatbázisban!";
    public static final String A_SZAVAZAST_VEZETO_ELNOKNEK_IS_KOTELEZO_SZAVAZNIA = "A szavazást vezető elnöknek is kötelező szavaznia!";
    public static final String EGY_KEPVISELO_NEM_SZAVAZHAT_TOBBSZOR_UGYANAZON_A_SZAVAZASON = "Egy képviselő nem szavazhat többször ugyanazon a szavazáson!";
    private final SzavazasRepository szavazasRepository;

    public void validateSzavazasMentes(SzavazasRequest request) {
        validateIdopontEgyedi(request);
        validateElnokSzavazott(request);
        validateKepviseloDuplikacio(request);
    }

    public void validateSzavazasId(String szavazasId) {
        if (!szavazasRepository.existsById(szavazasId)) {
            throw new NotFoundDomainBaseException(A_MEGADOTT_AZONOSITOJU_SZAVAZAS_NEM_TALALHATO);
        }
    }

    private void validateIdopontEgyedi(SzavazasRequest request) {
        if (szavazasRepository.existsByIdopont(request.idopont())) {
            throw new ValidationDomainBaseException(EZZEL_AZ_IDOPONTTAL_MAR_LETEZIK_SZAVAZAS_AZ_ADATBAZISBAN);
        }
    }

    private void validateElnokSzavazott(SzavazasRequest request) {
        boolean elnokSzavazott = request.szavazatok().stream()
                .anyMatch(szavazat -> szavazat.kepviselo().equalsIgnoreCase(request.elnok()));

        if (!elnokSzavazott) {
            throw new ValidationDomainBaseException(A_SZAVAZAST_VEZETO_ELNOKNEK_IS_KOTELEZO_SZAVAZNIA);
        }
    }

    private void validateKepviseloDuplikacio(SzavazasRequest request) {
        long egyediKepviselokSzama = request.szavazatok().stream()
                .map(SzavazatRequest::kepviselo)
                .distinct()
                .count();

        if (egyediKepviselokSzama < request.szavazatok().size()) {
            throw new ValidationDomainBaseException(EGY_KEPVISELO_NEM_SZAVAZHAT_TOBBSZOR_UGYANAZON_A_SZAVAZASON);
        }
    }
}
