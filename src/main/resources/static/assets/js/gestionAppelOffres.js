$(".demande-resume").on("click",function (){
    console.log("trigger click");
    let idAppelOffre = $(this).attr('id');
    $.ajax({
        url: '/AppelOffreDetail',
        dataType: 'html',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(idAppelOffre),
        processData: false,
        success: function( data, textStatus, jQxhr ){
            console.log( $("#update-besoin-modal-body"));
            $("#send-demande-modal-body").html(data);
            document.getElementsByClassName("alert-body")[0].remove();
            console.log("ffdfdf"+document.getElementsByClassName("alert-body")[0]);

        },
        error: function( jqXhr, textStatus, errorThrown ){
            Swal.fire({
                icon: 'error',
                title: 'error',
                text: 'erreur inconue.',
                customClass: {
                    confirmButton: 'btn btn-success'
                }
            })
        }
    });
})

$(".delete-appel-offre-button").on('click', function () {
    let appelOffreId = $(this).attr("id");
    console.log(appelOffreId);
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        customClass: {
            confirmButton: 'btn btn-primary',
            cancelButton: 'btn btn-outline-danger ms-1'
        },
        buttonsStyling: false
    }).then(function (result) {
        if (result.value) {
            $.ajax({
                url: '/deleteAppelOffre',
                dataType: 'text',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(appelOffreId),
                processData: false,
                success: function( data, textStatus, jQxhr ){
                    Swal.fire({
                        icon: 'success',
                        title: 'Deleted!',
                        text: 'Your file has been deleted.',
                        customClass: {
                            confirmButton: 'btn btn-success'
                        }
                    }).then(function (result) {
                        window.location.reload();
                    });
                },
                error: function( jqXhr, textStatus, errorThrown ){
                    Swal.fire({
                        icon: 'error',
                        title: 'error',
                        text: 'erreur inconue.',
                        customClass: {
                            confirmButton: 'btn btn-success'
                        }
                    })
                }
            });

        }
    });
});








//form.addEventListener('submit', handleSubmit);