package domain.services.XMLyTXT;

import dao.transfers.BackupEnDocumentos;
import domain.model.ErrorC;
import domain.model.springJDBC.Order;
import domain.model.xml.OrderXML;
import io.vavr.control.Either;
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

    public Either<ErrorC, Integer> saveOrderInTXT(Order order) {
        return backupEnDocumentos.saveOrderInTXT(order);
    }
}
