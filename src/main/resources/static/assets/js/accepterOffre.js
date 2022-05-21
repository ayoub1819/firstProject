
$(".accepter-offre-button").on('click', function () {
    let OffreId = $(this).attr("id");
    Swal.fire({
        title: 'etes vous sure ?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, accepter!',
        customClass: {
            confirmButton: 'btn btn-primary',
            cancelButton: 'btn btn-outline-danger ms-1'
        },
        buttonsStyling: false
    }).then(function (result) {
        if (result.value) {
            $.ajax({
                url: "/accepterOffre",
                dataType: 'text',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(OffreId),
                processData: false,
                success: function( data, textStatus, jQxhr ){
                    Swal.fire({
                        icon: 'success',
                        title: 'successé!',
                        text: 'Offre est accepté.',
                        customClass: {
                            confirmButton: 'btn btn-success'
                        }
                    }).then(function (result) {
                        window.location.reload();
                    });

                },
                error: function( jqXhr, textStatus, errorThrown ){
                    toastr['error']('l\'enregistrement a echoué.', 'Echec!', {
                        closeButton: true,
                        tapToDismiss: false,
                        rtl: false
                    });
                }
            });
        }
    });
});


