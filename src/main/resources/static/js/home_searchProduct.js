function submitForm()
{
    var selectedBrands = [];
    document.querySelectorAll('input[name="selectedBrands"]:checked').forEach(function(checkbox)
    {
        selectedBrands.push(checkbox.value);
    });

    var url = window.location.pathname;
    var params = new URLSearchParams(window.location.search);

    params.set('listBrand', selectedBrands.join(','));

    window.location.search = params.toString();
}