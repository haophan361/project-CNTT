$(document).ready(function()
{
    const currentHash = window.location.hash;
    if (currentHash === '#tab2')
    {
        loadTabContent('rating');
    }
    else
    {
        loadTabContent('description');
    }
});

function loadTabContent(tabName)
{
    let tabID;
    if (tabName === 'description')
    {
        tabID = '#tab1';
    }
    else
    {
        tabID = '#tab2';
    }
    const urlParts = window.location.pathname.split('/');
    const ID = urlParts[urlParts.length - 1];

    $.ajax(
        {
        url: '/web/' + tabName + '_product/' + ID,
        type: 'GET',
        success: function(data)
        {
            $(tabID).html(data);
            $('.tab-pane').removeClass('in active');
            $(tabID).addClass('in active');

            $('.tab-nav .tab').removeClass('active');
            $('a[href="'+tabID+'"]').parent().addClass('active');

            history.pushState(null, '', window.location.pathname + '#' + tabID.substring(1));
        },
        error: function(xhr, status, error)
        {
            console.error("Error loading tab content: ", error);
        }
    });
}
