function searchProduct()
{
    var keyword = document.getElementById('keyword').value;
    var selectedBrands = [];
    var selectedProductType=[];
    document.querySelectorAll('input[name="selectedBrands"]:checked').forEach(function(checkbox)
    {
        selectedBrands.push(checkbox.value);
    });
    document.querySelectorAll('input[name="selectedProductTypes"]:checked').forEach(function(checkbox)
    {
        selectedProductType.push(checkbox.value);
    });
    var url = new URL(window.location.origin + "/");
    var params = new URLSearchParams(window.location.search);
    params.set('keyword', keyword);
    if(selectedBrands)
    {
        params.set('listProductType', selectedProductType.join(','));
    }
    if(selectedProductType)
    {
        params.set('listBrand', selectedBrands.join(','));
    }
    window.location.href = url.pathname + '?' + params.toString();
}