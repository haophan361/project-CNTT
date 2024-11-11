
$('.input-number').each(function()
{
    var $this = $(this),
        $input = $this.find('input[type="number"]'),
        up = $this.find('.qty-up'),
        down = $this.find('.qty-down'),
        min = parseInt($input.attr('min')) || 1,
        max = parseInt($input.attr('max')),
        productID = $this.data('product-id');

    var initial_value = parseInt($input.val());
    down.on('click', function ()
    {
        var value = parseInt($input.val()) - 1;
        value = value < min ? min : value;
        if(initial_value!==value)
        {
            $input.val(value);
            $input.change();
            updateCart(productID);
            initial_value=value;
        }
    });

    up.on('click', function ()
    {
        var value = parseInt($input.val()) + 1;
        value = value > max ? max : value;
        if(initial_value!==value)
        {
            $input.val(value);
            $input.change();
            updateCart(productID);
            initial_value=value;
        }
        else
        {
            var modal = document.getElementById("customMessageBox");
            var messageText = document.getElementById("messageText");
            messageText.textContent = 'Số lượng sản phẩm mua được đã đạt tối đa';
            modal.style.display = "block";
        }
    });

    let debounceTimer;

    function updateCart(productID)
    {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(function ()
        {
            var quantity = $('#' + productID).val();
            $.ajax({
                url: "/user/updateCart/"+productID +"/" +quantity,
                type: "POST",
                success: function(response)
                {
                    window.location.href = response;
                }
            });
        }, 500);
    }
});
function deleteCart(cartID)
{
    {
        $.ajax({
            url:"/user/deleteCart/"+cartID,
            type: "POST",
            success: function(response)
            {
                window.location.href = response;
            }
        })
    }
}
function toggleCheckboxes(source)
{
    const checkboxes = document.querySelectorAll('.product-checkbox');
    checkboxes.forEach(checkbox =>
    {
        checkbox.checked = source.checked;
    });
}
function getCheckBox(source)
{
    const checkboxes = source.querySelectorAll('.product-checkbox');
    let isChecked = false;

    for (const checkbox of checkboxes)
    {
        if (checkbox.checked)
        {
            const product_selected=parseInt(checkbox.getAttribute('data-product-selected'));
            const product_available=parseInt(checkbox.getAttribute('data-product-available'));
            const product_name=checkbox.getAttribute('data-product-name');
            if(product_selected>product_available)
            {
                var modal = document.getElementById("customMessageBox");
                var messageText = document.getElementById("messageText");
                messageText.textContent = 'Số lượng sản phẩm '+ product_name +' chỉ còn '+ product_available +' không đủ để đặt hàng';
                modal.style.display = "block";
                return false;
            }
            isChecked = true;
        }
    }
    if (!isChecked)
    {
        var modal = document.getElementById("customMessageBox");
        var messageText = document.getElementById("messageText");
        messageText.textContent = 'Vui lòng chọn ít nhất một sản phẩm thanh toán.'
        modal.style.display = "block";
        return false;
    }
    return true;
}

document.querySelector(".close").onclick = function()
{
    document.getElementById("customMessageBox").style.display = "none";
}

window.onclick = function(event)
{
    var modal = document.getElementById("customMessageBox");
    if (event.target === modal)
    {
        modal.style.display = "none";
    }
}