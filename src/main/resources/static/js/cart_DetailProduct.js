function checkQuantityBeforeAdd(button)
{
    var ID = button.dataset.productId;
    var availableQuantity = button.dataset.quantity;
    var selectedQuantity = button.dataset.selected;
    var role = button.dataset.role;
    var modal;
    var messageText;
    if(role!=="[ROLE_USER]")
    {
        bootbox.alert({
            message: "Chuyển qua account người dùng để có thể mua hàng",
            backdrop: false
        });
        return;
    }
    if(availableQuantity===0)
    {
        modal = document.getElementById("customMessageBox");
        messageText = document.getElementById("messageText");
        messageText.textContent = 'Sản phẩm đã hết hàng';
        modal.style.display = "block";
        return;
    }
    var quantity = document.getElementById("quantity_input").value;
    if (quantity > availableQuantity-selectedQuantity)
    {
        modal = document.getElementById("customMessageBox");
        messageText = document.getElementById("messageText");
        messageText.textContent = 'Bạn đã có ' + selectedQuantity + ' sản phẩm trong giỏ hàng.\n' +
            'Không thể thêm số lượng đã chọn vào giỏ hàng vì sẽ vượt quá giới hạn mua hàng của bạn.';
        modal.style.display = "block";
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
function allowComment(source)
{
    const selectedRate = source.querySelector('input[name="rate"]:checked');
    if (!selectedRate)
    {
        var modal = document.getElementById("customMessageBox");
        var messageText = document.getElementById("messageText");
        messageText.textContent = 'Vui lòng chọn số sao để đánh giá trước khi gửi.';
        modal.style.display = "block";
        return false;
    }
    const allowComment=document.querySelector('.allowComment');
    var allowCommentTime=parseInt(allowComment.getAttribute('data-allow-comment'));
    if(allowCommentTime>0)
    {
        return true;
    }
    else
    {
        var modal = document.getElementById("customMessageBox");
        var messageText = document.getElementById("messageText");
        messageText.textContent = 'Số lần đánh giá của bạn đã vượt quá số lần mua sản phẩm của bạn. Để được đánh giá hãy mua sản phẩm.';
        modal.style.display = "block";
        return false;
    }
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