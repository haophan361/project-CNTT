function confirmDelete(event)
{
    const productID = event.target.closest("form").querySelector("input[name='productID']").getAttribute("data-id");
    event.preventDefault();
    bootbox.confirm({
        title: "Xác nhận xóa sản phẩm",
        message: "Bạn có muốn xóa sản phẩm này hay không",
        backdrop: false,
        buttons:
            {
                cancel:
                    {
                        label: '<i class="fa fa-times"></i> Không'
                    },
                confirm:
                    {
                        label: '<i class="fa fa-check"></i> Xác nhận'
                    }
            },
        callback: function (result)
        {
            if (result)
            {
                document.getElementById("deleteProduct_"+productID).submit();
            }
        }
    }).on('shown.bs.modal', function()
    {
        $(this).find('.modal-dialog').css('max-width', '40%');
    });
}
