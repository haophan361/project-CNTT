$(document).ready(function()
{
    const currentHash = window.location.hash;
    if (currentHash === '#unpaid')
    {
        fetchData('unpaid');
    }
    else if (currentHash === '#completed')
    {
        fetchData('completed');
    }
    else
    {
        fetchData('all');
    }
});
function fetchData(billType)
{
    let url;

    if (billType === 'all')
    {
        url = "/user/listBill";
    }
    else if (billType === 'unpaid')
    {
        url = "/user/listBill_Unpaid";
    }
    else if (billType === 'paid')
    {
        url = "/user/listBill_Paid";
    }
    else if (billType === 'completed')
    {
        url = "/user/listBill_Success";
    }
    $.ajax(
        {
        url: url,
        type: 'GET',
        success: function(data)
        {
            $('.list-bill').html(data);
            $('.bill-tab').removeClass('active');
            $('li[onclick="fetchData(\'' + billType + '\')"]').addClass('active');
            history.pushState(null, '', window.location.pathname + '#' + billType);
        }
    });
}

function updateStatus(source)
{
    const statusBill= source.closest('.statusBill')
    var billID=parseInt(statusBill.getAttribute("data-id"))
    {
        $.ajax({
            url:"/user/updateStatus/"+billID,
            type: "POST",
            success: function(response)
            {
                window.location.href = response;
            }
        })
    }
}