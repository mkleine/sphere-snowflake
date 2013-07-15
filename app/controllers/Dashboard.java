package controllers;

import com.google.common.collect.HashMultimap;
import controllers.actions.SaveContext;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.CustomerService;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.Product;
import play.mvc.Result;
import play.mvc.With;
import sphere.OrderService;
import sphere.ProductService;
import sphere.ShopController;
import sphere.Sphere;
import views.html.dashboard;
import views.html.orders;

@With(SaveContext.class)
public class Dashboard extends ShopController {

    public static Result show() {
        final Sphere sphere = sphere();
        final ProductService productService = sphere.products();
        // TODO we shouldn't fetch all - is there another way to find out how many items we have in total?
        final SearchResult<Product> products = productService.all().fetch();
        final OrderService orderService = sphere.orders();
        final QueryResult<Order> orders = orderService.all().fetch();
        final CustomerService customerService = sphere.client().customers();
        final QueryResult<Customer> customers = customerService.all().fetch();
        // TODO better load orders on demand for a given customer
        final HashMultimap<String, Order> ordersByCustomerId = HashMultimap.create(customers.getTotal(), 2);
        for(Order order : orders.getResults()) {
            ordersByCustomerId.put(order.getCustomerId(), order);
        }

        return ok(dashboard.render(products, customers, orders, ordersByCustomerId));
    }

    // TODO think about moving this method somewhere else
    public static Result displayOrder(String id) {
        final Order order = sphere().client().orders().byId(id).fetch().get();
        return ok(orders.render(order));
    }
}
