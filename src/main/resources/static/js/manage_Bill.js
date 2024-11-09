document.querySelectorAll('.bill-row').forEach(function(row)
{
    row.addEventListener('click', function(event)
    {
        if (event.target.type === 'checkbox')
        {
            event.stopPropagation();
            return;
        }
        var billID = row.getAttribute('data-bill-id');
        window.location.href = '/admin/billDetail?billID=' + billID;
    });

    row.addEventListener('mouseover', function()
    {
        row.style.backgroundColor = '#f0f0f0';
    });

    row.addEventListener('mouseout', function()
    {
        row.style.backgroundColor = 'inherit';
    });
});
function getCheckBox(source)
{
    const checkboxes = source.querySelectorAll('.bill-checkbox');
    let isChecked = false;

    for (const checkbox of checkboxes)
    {
        if (checkbox.checked)
        {
            isChecked = true;
            const receive_date = checkbox.getAttribute('data-bill-receive');
            const row = checkbox.closest('tr');
            const billID = row.getAttribute('data-bill-id');

            if (receive_date === null)
            {
                bootbox.alert({
                    message: 'Hóa đơn có mã là ' + billID + ' phải được hoàn tất giao hàng trên 30 ngày mới có thể xóa',
                    size: 'medium',
                    backdrop: false
                });
                return false;
            }

            const receiveDate = new Date(receive_date);
            const thirtyDaysAgo = new Date();
            thirtyDaysAgo.setDate(thirtyDaysAgo.getDate() - 30);

            if (receiveDate > thirtyDaysAgo)
            {
                bootbox.alert(
                    {
                    message: 'Hóa đơn có mã là ' + billID + ' phải được hoàn tất giao hàng trên 30 ngày mới có thể xóa',
                    size: 'large',
                    backdrop: false
                });
                return false;
            }
        }
    }

    if (!isChecked)
    {
        bootbox.alert(
            {
            message: 'Vui lòng chọn ít nhất một hóa đơn để xóa.',
            size: 'large',
            backdrop: true
        });
        return false;
    }
    return true;
}
function confirmDeleteBill(event)
{
    event.preventDefault();
    if (!getCheckBox(document.getElementById("deleteBillForm")))
    {
        return;
    }
    bootbox.confirm({
        title: "Xác nhận xóa đơn hàng",
        message: "Bạn có muốn xóa các hóa đơn này không",
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
                document.getElementById("deleteBillForm").submit();
            }
        }
    }).on('shown.bs.modal', function()
    {
        $(this).find('.modal-dialog').css("width","800px");
    });
}
