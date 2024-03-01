package dao.transfers.implementations;

import common.configuration.ConfigurationXML;
import dao.transfers.BackupEnDocumentos;
import domain.model.ErrorC;
import domain.model.springJDBC.Order;
import domain.model.xml.OrderXML;
import domain.model.xml.OrdersXML;
import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
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

    Path filePath = Paths.get(ConfigurationXML.getInstance().getProperty("pathListOrders"));

    @Override
    public Either<ErrorC, Integer> saveOrderInTXT(Order order) {
        Either<ErrorC,Integer> either;
        //List<Order> orderList = getAll().get();
        //int maxOrderId = orderList.stream().map(Order::getOrderId).max(Integer::compareTo).orElse(0);
        //int newOrderId = maxOrderId + 1;

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {

            writer.newLine();
            //order.setOrderId(newOrderId);
            //order.setOrderDate(LocalDateTime.now());
            String orderSt = order.toStringTextFile();
            writer.write(orderSt);
            //orderList.add(order);

            either = Either.right(1);
        } catch (IOException e) {
            either = Either.left(new ErrorC(3, "Error on adding order", LocalDate.of(2004, 3, 31)));
        }
        return either;
    }
}
