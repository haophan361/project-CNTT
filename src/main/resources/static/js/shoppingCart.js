
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
            bootbox.alert({
                title: "Thông báo",
                message: "Số lượng sản phẩm mua được đã đạt tối đa",
                backdrop: false
            });
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
    bootbox.confirm({
        title: "Xóa sản phẩm khỏi giỏ hàng",
        message: "Bạn có muốn xóa sản phẩm này khỏi giỏ hàng ?",
        backdrop: false,
        buttons:
            {
                cancel:
                    {
                        label: '<i class="fa fa-times"></i> Không'
                    },
                confirm:
                    {
                        label: '<i class="fa fa-check"></i> Đồng ý'
                    }
            },
        callback: function (result)
        {
            if (result)
            {
                $.ajax({
                    url:"/user/deleteCart/"+cartID,
                    type: "POST",
                    success: function ()
                    {
                        bootbox.alert({
                            title: "Thông báo",
                            message: "Sản phẩm đã bị xóa khỏi giỏ hàng!",
                            backdrop: false,
                            callback: function ()
                            {
                                location.reload();
                            }
                        });
                    },
                    error: function ()
                    {
                        bootbox.alert({
                            title: "Cảnh báo",
                            message: "Có lỗi xảy ra khi xóa sản phẩm khỏi giỏ hàng!",
                            backdrop: false
                        });
                    }
                });
            }
        }
    }).on('shown.bs.modal', function()
    {
        $(this).find('.modal-dialog').css('max-width', '40%');
    });
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
                bootbox.alert({
                    title: "Thông báo",
                    message: 'Số lượng sản phẩm '+ product_name +' chỉ còn '+ product_available +' không đủ để đặt hàng',
                    backdrop: false
                });
                return false;
            }
            isChecked = true;
        }
    }
    if (!isChecked)
    {
        bootbox.alert({
            title: "Thông báo",
            message: "Vui lòng chọn ít nhất một sản phẩm thanh toán.",
            backdrop: false
        });
        return false;
    }
    return true;
}