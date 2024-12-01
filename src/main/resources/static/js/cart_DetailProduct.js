function checkQuantityBeforeAdd(button)
{
    var ID = button.dataset.productId;
    var availableQuantity = button.dataset.quantity;
    var selectedQuantity = button.dataset.selected;
    var role = button.dataset.role;
    if(role!=="[ROLE_USER]")
    {
        bootbox.alert({
            title: "Thông báo",
            message: "Chuyển qua account người dùng để có thể mua hàng",
            backdrop: false
        });
        return;
    }
    if(availableQuantity===0)
    {
        bootbox.alert({
            title: "Thông báo",
            message: "Sản phẩm đã hết hàng",
            backdrop: false
        });
        return;
    }
    var quantity = document.getElementById("quantity_input").value;
    if (quantity > availableQuantity-selectedQuantity)
    {
        bootbox.alert({
            title: "Thông báo",
            message: 'Bạn đã có ' + selectedQuantity + ' sản phẩm trong giỏ hàng.\n' +
                'Không thể thêm số lượng đã chọn vào giỏ hàng vì sẽ vượt quá giới hạn mua hàng của bạn.',
            backdrop: false
        });
    }
    else
    {
        addToCart(ID);
    }
}
function addToCart(ID)
{
    var quantity = document.getElementById("quantity_input").value;
    {
        $.ajax({
            url:"/user/addToCart/"+ID+"/"+quantity,
            type: "POST",
            success: function(response)
            {
                window.location.href = response;
            }
        })
    }
}
function allowComment(button)
{
    var role = button.dataset.role;
    if (role !== "[ROLE_USER]")
    {
        bootbox.alert({
            title: "Thông báo",
            message: "Chỉ có người dùng mới có thể bình luận",
            backdrop: false
        });
        return false;
    }
    const selectedRate = document.querySelector('input[name="rate"]:checked');
    if (!selectedRate)
    {
        bootbox.alert({
            title: "Thông báo",
            message: "Vui lòng chọn số sao đánh giá trước khi gửi",
            backdrop: false
        });
        return false;
    }
    var allowCommentTime = parseInt(button.getAttribute('data-allow-comment'));
    if (allowCommentTime <= 0)
    {
        bootbox.alert({
            title: "Thông báo",
            message: "Số lần đánh giá của bạn đã vượt quá số lần mua sản phẩm của bạn. Để được đánh giá hãy mua sản phẩm.",
            backdrop: false
        });
        return false;
    }

    var form = document.getElementById("commentForm");
    form.submit();
}