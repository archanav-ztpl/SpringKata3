package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Order Item API", description = "Operations related to order items (admin/support only)")
@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @Operation(summary = "Create a new order item", description = "Creates a new order item (admin/support only).",
        security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order item created successfully", content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Order item creation payload",
        required = true,
        content = @Content(schema = @Schema(implementation = OrderItemDto.class))
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPPORT')")
    @PostMapping
    public ResponseEntity<OrderItemDto> createOrderItem(@RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.ok(orderItemService.createOrderItem(orderItemDto));
    }

    @Operation(summary = "Get order item by ID", description = "Retrieve an order item by its unique ID (admin/support only).",
        security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order item found", content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
        @ApiResponse(responseCode = "404", description = "Order item not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN','SUPPORT')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getOrderItem(@Parameter(description = "ID of the order item to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @Operation(summary = "Get all order items", description = "Retrieve a list of all order items (admin/support only).",
        security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of order items", content = @Content(schema = @Schema(implementation = OrderItemDto.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN','SUPPORT')")
    @GetMapping
    public ResponseEntity<List<OrderItemDto>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @Operation(summary = "Update order item", description = "Update an existing order item (admin/support only).",
        security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order item updated successfully", content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
        @ApiResponse(responseCode = "404", description = "Order item not found", content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Order item update payload",
        required = true,
        content = @Content(schema = @Schema(implementation = OrderItemDto.class))
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPPORT')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDto> updateOrderItem(
        @Parameter(description = "ID of the order item to update") @PathVariable Long id,
        @RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(id, orderItemDto));
    }

    @Operation(summary = "Delete order item", description = "Delete an order item by its ID (admin/support only).",
        security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Order item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Order item not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN','SUPPORT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@Parameter(description = "ID of the order item to delete") @PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
