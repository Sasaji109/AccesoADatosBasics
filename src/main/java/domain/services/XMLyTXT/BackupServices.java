package domain.services.XMLyTXT;

import dao.transfers.BackupEnDocumentos;
import domain.xml.OrderXML;
import jakarta.inject.Inject;
import java.util.List;

public class BackupServices {
    private BackupEnDocumentos backupEnDocumentos;

    @Inject
    public BackupServices(BackupEnDocumentos backupEnDocumentos) {
        this.backupEnDocumentos = backupEnDocumentos;
    }

    public boolean backupOrdersToXml(List<OrderXML> ordersList, String customerName) throws Exception {
        return backupEnDocumentos.backupOrdersToXml(ordersList, customerName);
    }
}
