$(document).ready(function () {
    const currentHash = window.location.hash;
    if (currentHash === '#unpaid') {
        fetchData('unpaid');
    } else if (currentHash === '#completed') {
        fetchData('completed');
    } else {
        fetchData('all');
    }
});

function fetchData(billType) {
    let url;

    if (billType === 'all') {
        url = "/user/listBill";
    } else if (billType === 'unpaid') {
        url = "/user/listBill_Unpaid";
    } else if (billType === 'paid') {
        url = "/user/listBill_Paid";
    } else if (billType === 'completed') {
        url = "/user/listBill_Success";
    } else if (billType === 'waiting') {
        url = "/user/listBill_Waiting"
    }
    $.ajax(
        {
            url: url,
            type: 'GET',
            success: function (data) {
                $('.list-bill').html(data);
                $('.bill-tab').removeClass('active');
                $('li[onclick="fetchData(\'' + billType + '\')"]').addClass('active');
                history.pushState(null, '', window.location.pathname + '#' + billType);
            }
        });
}

function updateStatus(source)
{
    const statusBill = source.closest('.statusBill')
    var billID = parseInt(statusBill.getAttribute("data-id"))
    {
        $.ajax({
            url: "/user/updateStatus/" + billID,
            type: "POST",
            success: function (response)
            {
                window.location.href = response;
            }
        })
    }
}
function confirmCancel(event,button)
{
    var status = button.dataset.status;
    bootbox.confirm({
        title: "Xác nhận hủy đơn hàng",
        message: "Bạn có muốn hủy đơn hàng hay không",
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
                if(status==="1")
                {
                    bootbox.alert({
                        title: "Thông báo",
                        message: "Tiền đã thanh toán sẽ được hoàn lại trong vòng 24h!",
                        backdrop: false,
                        callback: function ()
                        {
                            document.getElementById("cancelForm").submit();
                        }
                    });
                }
                else
                {
                    document.getElementById("cancelForm").submit();
                }
            }
        }
    }).on('shown.bs.modal', function()
    {
        $(this).find('.modal-dialog').css('max-width', '40%');
    });
}
