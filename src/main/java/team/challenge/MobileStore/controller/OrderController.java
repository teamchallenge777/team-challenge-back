package team.challenge.MobileStore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.challenge.MobileStore.exception.ApiError;
import team.challenge.MobileStore.model.Brand;
import team.challenge.MobileStore.model.Customer;
import team.challenge.MobileStore.model.Order;
import team.challenge.MobileStore.service.OrderService;

import java.util.List;
import java.util.Map;

//api/v1/customers/{customer_id}/orders/
@RestController
@RequestMapping("api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found all orders",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Orders weren't found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    })
    })
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Get one order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found one order by ID",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order with present ID wasn't found!",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    })
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOneOrderById(orderId));
    }

    @Operation(summary = "Create one order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Create one order"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order wasn't created",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    })
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody Customer customer,
            @RequestParam String deliveryType,
            @RequestParam String paymentType
    ) {

        return ResponseEntity.ok(orderService.createOrder(customer, deliveryType, paymentType));
    }

    @Operation(summary = "Delete one order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Delete one order by ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order with present ID not found!",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    })
    })
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order was deleted");
    }

    @Operation(summary = "Update Order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated order info.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Brand.class))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Order with present ID not found!",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    })
    })
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String orderId, @RequestBody Order updatedOrder) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, updatedOrder));
    }
}