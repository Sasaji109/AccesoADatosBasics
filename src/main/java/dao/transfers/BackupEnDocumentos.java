package dao.transfers;

import domain.model.ErrorC;
import domain.model.springJDBC.Order;
import domain.model.xml.OrderXML;
import io.vavr.control.Either;

import java.util.List;

public interface BackupEnDocumentos {

    boolean backupOrdersToXml(List<OrderXML> ordersList, String customerName) throws Exception;

    Either<ErrorC, Integer> saveOrderInTXT(Order order);
}
