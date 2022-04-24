$(".signaler-panne").on("click",function() {
    let id_panne = $(this).attr("id");
    $("#id_panne").val(id_panne);

});


function ajouterConstat(event) {
    event.preventDefault();

    const data = new FormData(event.target);
    const id = data.get('id');
    const constat = data.get('constat');

    let panne = {id:id,constat:constat};
    console.log(panne);
    Swal.fire({
        title: 'une le fois le constat envoyer vous pouvez pas l\'annuler ?',
        text: "le constat : "+constat,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, envoyer!',
        customClass: {
            confirmButton: 'btn btn-primary',
            cancelButton: 'btn btn-outline-danger ms-1'
        },
        buttonsStyling: false
    }).then(function (result) {
        if (result.value) {
            $.ajax({
                url: "/addConstat",
                dataType: 'text',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(panne),
                processData: false,
                success: function( data, textStatus, jQxhr ){
                    Swal.fire({
                        icon: 'success',
                        title: 'successé!',
                        text: 'votre constat à été bien envoyer.',
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



}
$("form").on("submit",ajouterConstat);