package dao.transfers.implementations;

import dao.transfers.BackupEnDocumentos;
import domain.xml.OrderXML;
import domain.xml.OrdersXML;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class BackupEnDocumentosImpl implements BackupEnDocumentos {

    @Override
    public boolean backupOrdersToXml(List<OrderXML> ordersList, String customerName) throws Exception {
        String fileName = customerName + "_orders_backup.xml";
        File backupFile = new File(fileName);

        JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        OrdersXML orders = new OrdersXML();
        orders.setOrdersList(ordersList);

        try (OutputStream outputStream = new FileOutputStream(backupFile)) {
            marshaller.marshal(orders, outputStream);
        }

        return true;
    }
}
