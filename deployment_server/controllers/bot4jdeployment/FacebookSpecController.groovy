package bot4jdeployment

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FacebookSpecController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def botController = new BotController()


    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FacebookSpec.list(params), model:[facebookSpecCount: FacebookSpec.count()]
    }

    def show(FacebookSpec facebookSpec) {
        respond facebookSpec
    }

    def create() {
        def facebookSpec = new FacebookSpec(params)
        def bot = Bot.get(params.long('botId'))
        facebookSpec.setBot(bot)
        respond facebookSpec
    }

    @Transactional
    def save(FacebookSpec facebookSpec) {
        if (facebookSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (facebookSpec.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond facebookSpec.errors, view:'create'
            return
        }

        facebookSpec.save flush:true

        redirect controller:'bot', action:'show', params:[id:facebookSpec.bot.id]
    }

    def edit(FacebookSpec facebookSpec) {
        respond facebookSpec
    }

    @Transactional
    def update(FacebookSpec facebookSpec) {
        if (facebookSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (facebookSpec.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond facebookSpec.errors, view:'edit'
            return
        }

        facebookSpec.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'facebookSpec.label', default: 'FacebookSpecPayload'), facebookSpec.id])
                redirect facebookSpec
            }
            '*'{ respond facebookSpec, [status: OK] }
        }
    }

    @Transactional
    def delete(FacebookSpec facebookSpec) {

        if (facebookSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        facebookSpec.delete flush:true

        redirect controller:'bot', action:'show', params:[id:facebookSpec.bot.id]
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookSpec.label', default: 'FacebookSpecPayload'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
