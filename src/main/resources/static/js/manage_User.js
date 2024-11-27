function searchUser()
{
    var name = document.getElementById('name').value;
    var username=document.getElementById('username').value
    var phone=document.getElementById('phone').value
    var role = [];
    document.querySelectorAll('input[name="role"]:checked').forEach(function (checkbox)
    {
        role.push(checkbox.value);
    });
    var url = new URL(window.location.origin + "/admin/user");
    var params = new URLSearchParams(window.location.search);
    params.set('name', name);
    params.set('username',username)
    params.set('phone',phone)
    if (role)
    {
        params.set('role', role.join(','));
    }
    window.location.href = url.pathname + '?' + params.toString();
}
function RoleChange(selectedRole)
{
    var username = selectedRole.dataset.email;
    var name = selectedRole.dataset.name;
    var currentRole = selectedRole.dataset.role;
    var newRole = selectedRole.value;
    bootbox.confirm({
        title: "Chuyển đổi vai trò",
        message: "Bạn có muốn chuyển vai trò  của "+name +" từ "+currentRole+" thành "+newRole+"?",
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
                $.ajax({
                    url: '/admin/changeRole',
                    type: 'POST',
                    data: {
                        username: username,
                        role: newRole
                    },
                    success: function (response)
                    {
                        bootbox.alert({
                            message: "Vai trò đã được cập nhật thành công!",
                            backdrop: false
                        });
                    },
                    error: function ()
                    {
                        bootbox.alert({
                            message: "Có lỗi xảy ra khi cập nhật vai trò!",
                            backdrop: false
                        });
                    }
                });
            }
            else
            {
                selectedRole.value=currentRole;
            }
        }
    }).on('shown.bs.modal', function()
    {
        $(this).find('.modal-dialog').css('max-width', '80%');
    });
}