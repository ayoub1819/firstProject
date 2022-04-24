$(".signaler-panne").on("click",function() {
    let id_panne = $(this).attr("id");
    $("#id_panne").val(id_panne);

});


function ajouterConstat(event) {
    event.preventDefault();

    const data = new FormData(event.target);
    const id = data.get('id');
    let decision = data.get('decision');

    let panne = {id:id,etat:decision};
    console.log(panne);
    if (decision == "envoyer") decision = "envoyer le constat au fournisseur";
    Swal.fire({
        title: 'une le fois la desision est prise  vous ne pouvez pas la changer ?',
        text: "votre decision : "+decision,
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
                url: "/makeDecision",
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