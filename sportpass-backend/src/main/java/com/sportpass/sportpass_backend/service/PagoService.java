package com.sportpass.sportpass_backend.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import com.sportpass.sportpass_backend.dto.CompraDTO;
import com.sportpass.sportpass_backend.model.EventoZona;
import com.sportpass.sportpass_backend.repository.EventoZonaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    private final EventoZonaRepository eventoZonaRepository;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public CompraDTO.PagoResponse crearPreferencia(CompraDTO.PagoRequest request) {
        try {
            EventoZona eventoZona = eventoZonaRepository.findById(request.getEventoZonaId())
                    .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

            String nombreEvento = eventoZona.getEvento().getNombre();
            String nombreZona = eventoZona.getZona().getNombre();
            BigDecimal precio = eventoZona.getPrecio();
            int cantidad = request.getCantidad();

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(nombreEvento + " - " + nombreZona)
                    .quantity(cantidad)
                    .unitPrice(precio)
                    .currencyId("ARS")
                    .build();

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:4200/checkout/resultado?status=success")
                    .failure("http://localhost:4200/checkout/resultado?status=failure")
                    .pending("http://localhost:4200/checkout/resultado?status=pending")
                    .build();

            // Guardamos los datos de la compra en metadata para procesarla después
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .backUrls(backUrls)
                    .externalReference(
                        request.getEventoZonaId() + "|" +
                        (request.getAsientoId() != null ? request.getAsientoId() : "null") + "|" +
                        cantidad
                    )
                    .build();

            Preference preference = new PreferenceClient().create(preferenceRequest);

            CompraDTO.PagoResponse response = new CompraDTO.PagoResponse();
            response.setCheckoutUrl(preference.getSandboxInitPoint());
            response.setPreferenceId(preference.getId());
            return response;

        } catch (com.mercadopago.exceptions.MPApiException e) {
            System.out.println("MP API Error: " + e.getApiResponse().getContent());
            throw new RuntimeException("Error MP: " + e.getApiResponse().getContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear preferencia: " + e.getMessage());
        }
    }
}