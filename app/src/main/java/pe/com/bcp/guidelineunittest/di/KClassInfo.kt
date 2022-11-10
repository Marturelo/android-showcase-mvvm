package pe.com.bcp.guidelineunittest.di
import java.util.Optional
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class KClassInfo(
    private val id: String,
    private val className: String = id,
    private val type: Type = Type.Singleton,
//    val initMethod: Option<String> = none(),
    private var singletonInstance: Optional<Any> = Optional.empty<Any>()
) {
    constructor(
        id: String,
        klass: KClass<out Any>,
        type: Type = Type.Singleton,
        singletonInstance: Optional<Any> = Optional.empty<Any>()
    ) : this(id, klass.qualifiedName!!, type, singletonInstance) {
        clazz = Optional.of(klass)
    }

    enum class Type { Singleton, Prototype }

    fun getInstance(): Any = singletonInstance.orElse {
        val instance = getKClass().createInstance()
        if (type == Type.Singleton) singletonInstance = Optional.of(instance)
        Optional.of(instance)
    }

    private var clazz: Optional<KClass<out Any>> = Optional.empty()

    private fun getKClass(): KClass<out Any> = clazz.orElseGet {
        val kclass = Class.forName(className).kotlin as KClass<out Any>
        if (kclass.objectInstance != null) throw IllegalArgumentException("$className must be class, but it is object.")
        clazz = Optional.of(kclass)
        kclass
    }
}