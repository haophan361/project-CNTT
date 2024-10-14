function addToCart(ID)
{
    var quantity = document.getElementById("quantity_input").value;
    {
        $.ajax({
            url:"/user/addToCart/"+ID+"/"+quantity,
            type: "POST"
        })
    }
}