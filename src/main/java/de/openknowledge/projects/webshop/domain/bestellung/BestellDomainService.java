package de.openknowledge.projects.webshop.domain.bestellung;

import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;
import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellRepository;
import de.openknowledge.projects.webshop.infrastructure.zahlungsart.ZahlungsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BestellDomainService {

    private BestellRepository bestellRepository;

    private ZahlungsRepository zahlungsRepository;

    @Inject
    public BestellDomainService(BestellRepository bestellRepository, ZahlungsRepository zahlungsRepository) {
        this.bestellRepository = bestellRepository;
        this.zahlungsRepository = zahlungsRepository;
    }

    public Zahlung create(Bestellung bestellung) {
        bestellRepository.create(bestellung);

        Zahlung zahlung = Zahlung.Builder()
                .setBestellung(bestellung)
                .build();

        zahlungsRepository.create(zahlung);

        return zahlung;
    }
}
