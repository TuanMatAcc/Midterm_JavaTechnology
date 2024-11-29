function removeCartItem(productId) {
    fetch('/cart/remove?id=' + productId, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
        });
}

function decreaseCartItem(productId) {
    fetch('/cart/update/decrease?id=' + productId, {
        method: 'PUT',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
        });
}

function increaseCartItem(productId) {
    fetch('/cart/update/increase?id=' + productId, {
        method: 'PUT',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
        });
}

function decreaseQuantity(itemId, unitPrice, productId) {
    const input = document.querySelector(`#${itemId} .quantity-input`);
    let quantity = parseInt(input.value);
    if (quantity > 1) {
        quantity--;
        input.value = quantity;
        updatePrice(itemId, unitPrice, quantity);
        calculateTotalPrice();
        const currentQuantity = parseInt(document.getElementById('product-in-cart' + productId).value.split(" ")[1]);
        document.getElementById('product-in-cart' + productId).value = productId + " " + (currentQuantity - 1);
        decreaseCartItem(productId);
    }
}

function increaseQuantity(itemId, unitPrice, productId) {
    const input = document.querySelector(`#${itemId} .quantity-input`);
    let quantity = parseInt(input.value);
    quantity++;
    input.value = quantity;
    updatePrice(itemId, unitPrice, quantity);
    calculateTotalPrice();
    // Update the quantity in the hidden input field
    const currentQuantity = parseInt(document.getElementById('product-in-cart' + productId).value.split(" ")[1]);
    document.getElementById('product-in-cart' + productId).value = productId + " " + (currentQuantity + 1);
    increaseCartItem(productId);
}

function updatePrice(itemId, unitPrice, quantity) {
    const currentPriceElement = document.querySelector(`#${itemId} .current-price`);
    const totalPrice = unitPrice * quantity;
    currentPriceElement.textContent = "$" + totalPrice;
}

function calculateTotalPrice() {
    let total = 0;
    const items = document.querySelectorAll('.cart-item');
    items.forEach(item => {
    const quantity = parseInt(item.querySelector('.quantity-input').value);
    const unitPrice = parseInt(item.querySelector('.current-price').textContent.replace(/[^0-9]/g, ''));
    total += unitPrice;
    });
    document.querySelector('.summary p').textContent = `Subtotal (${items.length} items): ` + "$" +
    total;
}

function removeItem(itemId, productId) {
    const item = document.getElementById(itemId);
    item.remove();
    calculateTotalPrice();
    document.getElementById('product-in-cart' + productId).remove();
    removeCartItem(productId);
}
// Initial calculation of the total price on page load
calculateTotalPrice();

// Close the error message box on button click
document.getElementById('closeErrorButton').addEventListener('click', () => {
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.style.display = 'none'; // Hide the modal
});

// Automatically show the modal if it exists
document.addEventListener('DOMContentLoaded', () => {
    const errorMessage = document.getElementById('errorMessage');
    if (errorMessage) {
        errorMessage.style.display = 'block'; // Show the modal
    }
});

