package dao.transfers;

import domain.xml.OrderXML;
import java.util.List;

public interface BackupEnDocumentos {
    boolean backupOrdersToXml(List<OrderXML> ordersList, String customerName) throws Exception;
}
